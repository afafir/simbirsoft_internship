package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.dto.WriteoffDto;
import ru.simbirsoft.warehouse_management.dto.createForms.WriteoffCreateDto;

import java.util.List;

public interface WriteoffService {
    /**
     * Creates a new writeoff from Creating form.
     *
     *
     * @param writeoffCreateDto - writeoff create form. Contatins id of warehouse, ids of items and their counts
     * @return WriteoffDto - dto of created writeoff
     */
    WriteoffDto createWriteoff(WriteoffCreateDto writeoffCreateDto);

    /**
     * @param id - id of writeoff
     * @return dto of founded writeoff.
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id doesnt exist
     */
    WriteoffDto getWriteoffById(Long id);

    /**
     * Deletes writeoff from database. But if writeoff already confirmed, doesnt delete it
     * @param id - id of writeoff to delete
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id doesnt exist
     * @throws IllegalStateException if writeoff confirmed
     */
    void deleteById(Long id);

    /**
     * Confirm writeoff. It deletes items from the warehouse with the number of products specified in the writeoff
     *
     *
     * @param writeoffId - id of the writeoff
     * @throws IllegalStateException if writeoff already confirmed
     */
    void confirmWriteoff(Long writeoffId);

    /**
     * Return dto list of not confirmed writeoffs
     * @return list of not confirmed writeoffs
     */
    List<WriteoffDto> getNotConfirmed();
}
