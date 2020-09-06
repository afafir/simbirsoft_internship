package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.simbirsoft.warehouse_management.model.Writeoff;

import java.time.LocalDateTime;
import java.util.List;

/** Jpa repository for WriteOff entity */
public interface WriteoffRepository extends JpaRepository<Writeoff, Long> {
  /**
   * Returns list of writeoffs where confirmation status is false
   *
   * @return List of writeoffs
   */
  List<Writeoff> findByConfirmedFalse();

  /**
   * Returns list of writeoffs which were confirmed at the specified time
   *
   * @param start - time from data taken
   * @param end - time until data taken
   * @return List of writeoffs
   */
  @Query(
      "SELECT writeoff FROM Writeoff  writeoff WHERE "
          + "writeoff.confirmed = true "
          + "AND writeoff.time BETWEEN :start AND :end ")
  List<Writeoff> findAllBetweenDates(
      @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

  /**
   * Returns list of writeoffs which were confirmed at the specified time in the specified warehouse
   *
   * @param start - time from data taken
   * @param end - time until data taken
   * @param warehouseId - id of the warehouse
   * @return List of writeoffs
   */
  @Query(
      "SELECT writeoff FROM Writeoff  writeoff WHERE "
          + "writeoff.confirmed = true "
          + "AND writeoff.warehouseid = :warehouseId "
          + "AND writeoff.time BETWEEN :start AND :end ")
  List<Writeoff> findAllBetweenDatesForWarehouse(
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end,
      @Param("warehouseId") Long warehouseId);
}
