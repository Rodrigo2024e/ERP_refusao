package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.MeltingItemDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Melting;
import com.smartprocessrefusao.erprefusao.entities.MeltingItem;
import com.smartprocessrefusao.erprefusao.entities.PK.MeltingItemPK;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.MeltingItemRepository;
import com.smartprocessrefusao.erprefusao.repositories.MeltingRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

@Service
public class MeltingItemService {

	@Autowired
	private MeltingItemRepository meltingItemRepository;

	@Autowired
	private MeltingRepository meltingRepository;

	@Autowired
	private MaterialRepository materialRepository;

	private Melting validateMelting(Long meltingId) {
		return meltingRepository.findById(meltingId)
				.orElseThrow(() -> new ResourceNotFoundException("Melting não encontrado: id = " + meltingId));
	}

	private Material validateMaterial(Long code) {
		return materialRepository.findById(code)
				.orElseThrow(() -> new ResourceNotFoundException("Material não encontrado: code = " + code));
	}

	// --- MÉTODOS AUXILIARES DE PK ---

	/**
	 * Constrói a chave primária composta (MeltingItemPK) usando referências.
	 * 
	 * @param receiptId O ID da Receipt (parte da PK).
	 * @param code      do Material (parte da PK).
	 * @return A chave ReceiptItemPK totalmente inicializada.
	 */
	private MeltingItemPK buildMeltingItemPK(Long meltingId, Long code) {

		Melting melting = validateMelting(meltingId);
		Material material = validateMaterial(code);

		return new MeltingItemPK(melting, material);
	}

	@Transactional(readOnly = true)
	public List<MeltingItemDTO> findAll() {
		return meltingItemRepository.findAll().stream().map(MeltingItemDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public MeltingItemDTO findById(Long meltingtId, Long code) {
		MeltingItemPK pk = buildMeltingItemPK(meltingtId, code);

		MeltingItem entity = meltingItemRepository.findById(pk)
				.orElseThrow(() -> new ResourceNotFoundException("MeltingItem não encontrado para os IDs informados"));

		return new MeltingItemDTO(entity);
	}

	@Transactional
	public MeltingItemDTO insert(Long parentMeltingId, MeltingItemDTO dto) {

		// valida coerência do ID no corpo
		if (dto.getMeltingId() != null && !dto.getMeltingId().equals(parentMeltingId)) {
			throw new IllegalArgumentException("MeltingId no corpo (" + dto.getMeltingId()
					+ ") difere do ID passado na URL (" + parentMeltingId + ")");
		}

		// valida e monta a chave primária
		MeltingItemPK pk = buildMeltingItemPK(parentMeltingId, dto.getMaterialCode());

		if (meltingItemRepository.existsById(pk)) {
			throw new IllegalArgumentException(
					"meltingItem já existe com (Melting=" + parentMeltingId + ", Code=" + dto.getMaterialCode() + ")");
		}

		MeltingItem entity = new MeltingItem();
		entity.setId(pk);

		copyDtoToEntity(dto, entity);

		entity = meltingItemRepository.save(entity);
		return new MeltingItemDTO(entity);

	}

	@Transactional
	public MeltingItemDTO update(Long meltingId, Long code, MeltingItemDTO dto) {

		// 1️⃣ Valida Melting
		Melting melting = meltingRepository.findById(meltingId)
				.orElseThrow(() -> new ResourceNotFoundException("Fusão não encontrada. ID: " + meltingId));

		// 2️⃣ Valida Material
		Material material = materialRepository.findById(code)
				.orElseThrow(() -> new ResourceNotFoundException("Material não encontrado. Código: " + code));

		// 3️⃣ Monta PK corretamente
		MeltingItemPK pk = new MeltingItemPK();
		pk.setMelting(melting);
		pk.setMaterial(material);

		// 4️⃣ Busca o item da fusão
		MeltingItem entity = meltingItemRepository.findById(pk).orElseThrow(() -> new ResourceNotFoundException(
				"Item da fusão não encontrado para o material " + code + " na fusão " + meltingId));

		// 5️⃣ Atualiza campos
		copyDtoToEntity(dto, entity);

		entity = meltingItemRepository.save(entity);
		return new MeltingItemDTO(entity);
	}

	@Transactional
	public void delete(Long meltingId, Long code) {
		MeltingItemPK pk = buildMeltingItemPK(meltingId, code);

		// Se a validação for necessária
		if (!meltingItemRepository.existsById(pk)) {
			throw new ResourceNotFoundException("MeltingItem not found for deletion.");
		}

		meltingItemRepository.deleteById(pk);
	}

	// --- FUNÇÃO AUXILIAR DE MAPPING ---

	private void copyDtoToEntity(MeltingItemDTO dto, MeltingItem entity) {

		// Mapeamento dos campos que não fazem parte da PK:

		entity.setQuantity(dto.getQuantity());
		entity.setAveragePrice(dto.getAveragePrice());

		// 🧮 Recalcula o valor total (também está no @PrePersist/@PreUpdate da
		// entidade)
		if (dto.getQuantity() != null && dto.getAveragePrice() != null) {
			entity.setTotalValue(dto.getQuantity().multiply(dto.getAveragePrice()));
		} else {
			entity.setTotalValue(BigDecimal.ZERO);
		}

		entity.setAverageRecoveryYield(dto.getAverageRecoveryYield());

		// 🧮 Recalcula a quantidade de metal líquido com base no rendimento da sucata
		// (também está no @PrePersist/@PreUpdate da entidade)
		if (dto.getQuantity() != null && dto.getAverageRecoveryYield() != null) {
			entity.setQuantityMco(dto.getQuantity().multiply(dto.getAverageRecoveryYield()));
		} else {
			entity.setQuantityMco(BigDecimal.ZERO);
		}

		// 🧮 Recalcula a quantidade de Slag com base na diferença entre quantity -
		// quantityMco
		if (dto.getQuantity() != null && dto.getQuantityMco() != null) {
			entity.setSlagWeight(dto.getQuantity().subtract(dto.getQuantityMco()));
		} else {
			entity.setSlagWeight(BigDecimal.ZERO);
		}
	}
}