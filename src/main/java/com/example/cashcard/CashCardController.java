package com.example.cashcard;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Controller class for managing CashCard entities.
 * Acts as a RESTful API endpoint for handling CRUD operations related to CashCards.
 * The class is annotated with @RestController to indicate that it is a specialized
 * version of the @Controller stereotype that is suitable for RESTful web services.
 * The base URL for all requests related to this controller is "/cashcards".
 */
@RestController
@RequestMapping("/cashcards")
public final class CashCardController {

  /**
  * Repository interface for managing CashCard entities.
  * Provides methods for performing CRUD operations and querying CashCards.
  */
  private final CashCardRepository cashCardRepository;

  /**
  * Constructor for CashCardController class.
  * Initializes the CashCardController with a CashCardRepository.
  *
  * @param cashCardRepo The repository for CashCard entities.
  */
  public CashCardController(final CashCardRepository cashCardRepo) {
    this.cashCardRepository = cashCardRepo;
  }

  /**
   * Retrieves a CashCard by its ID.
   *
   * @param requestedId The ID of the requested CashCard.
   * @param principal The principal representing the authenticated user.
   * @return ResponseEntity with the CashCard if found, or not found status.
   */
  @GetMapping("/{requestedId}")
  public ResponseEntity<CashCard> findById(@PathVariable final Long requestedId,
                                           final Principal principal) {
    CashCard cashCard = findCashCard(requestedId, principal);
    return cashCard != null ? ResponseEntity.ok(cashCard) : ResponseEntity.notFound().build();
  }

  @PostMapping
  private ResponseEntity<Void> createCashCard(@RequestBody final CashCard newCashCardRequest,
                                              final UriComponentsBuilder ucb) {
    CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
    URI locationOfNewCashCard = ucb
            .path("cashcards/{id}")
            .buildAndExpand(savedCashCard.id())
            .toUri();
    return ResponseEntity.created(locationOfNewCashCard).build();
  }

  /**
   * Retrieves all CashCards.
   *
   * @param pageable The pagination information for retrieving the CashCards.
   * @param principal The principal representing the authenticated user.
   * @return ResponseEntity with a list of CashCards.
   */
  @GetMapping
  public ResponseEntity<List<CashCard>> findAll(final Pageable pageable,
                                                final Principal principal) {
    Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(),
            PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
            ));
    return ResponseEntity.ok(page.getContent());
  }

  @PutMapping("/{requestedId}")
  private ResponseEntity<Void> putCashCard(@PathVariable final Long requestedId,
                                           @RequestBody final CashCard cashCardUpdate,
                                           final Principal principal) {
    CashCard cashCard = findCashCard(requestedId, principal);
    if (cashCard != null) {
      CashCard updatedCashCard = new CashCard(
              cashCard.id(),
              cashCardUpdate.amount(),
              principal.getName()
      );
      cashCardRepository.save(updatedCashCard);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  private CashCard findCashCard(final Long requestedId,
                                final Principal principal) {
    return cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<Void> deleteCashCard(@PathVariable final Long id,
                                              final Principal principal) {
    if (cashCardRepository.existsByIdAndOwner(id, principal.getName())) {
      cashCardRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
