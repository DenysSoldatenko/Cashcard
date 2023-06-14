package com.example.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository interface for managing CashCard entities.
 */
public interface CashCardRepository extends CrudRepository<CashCard, Long>,
        PagingAndSortingRepository<CashCard, Long> {

  /**
   * Finds a CashCard by its ID and owner.
   *
   * @param id the ID of the CashCard
   * @param owner the owner of the CashCard
   * @return the found CashCard or null if not found
   */
  CashCard findByIdAndOwner(Long id, String owner);

  /**
   * Finds all CashCards by their owner, with pagination support.
   *
   * @param owner the owner of the CashCards
   * @param request the page request for pagination
   * @return a page of CashCards belonging to the specified owner
   */
  Page<CashCard> findByOwner(String owner, PageRequest request);

  /**
   * Checks if a CashCard with the given ID and owner exists.
   *
   * @param id    the ID of the CashCard
   * @param owner the owner of the CashCard
   * @return true if a CashCard with the given ID and owner exists, false otherwise
   */
  boolean existsByIdAndOwner(Long id, String owner);
}
