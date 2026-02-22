//package com.irestaurant.iPortalAPI.service;
//
//import com.irestaurant.iPortalAPI.objectbox.model.Account;
//import io.objectbox.Box;
//import io.objectbox.BoxStore;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
///**
// * @author iAccount Development Team
// * @version 1.0.0
// */
//@Service
//public class AccountService {
//
//    private final Box<Account> accountBox;
//
//    /**
//     * Constructor that initializes the AccountService with a BoxStore.
//     * The BoxStore is injected as a Spring bean for thread-safe operations.
//     * 
//     * @param boxStore
//     */
//    public AccountService(BoxStore boxStore) {
//        this.accountBox = boxStore.boxFor(Account.class);
//    }
//
//    /**
//     * Saves a account entity to the database.
//     * Uses ObjectBox's put operation which handles both insert and update.
//     * 
//     * @param account The account to save
//     * @return The ID of the saved account
//     */
//    public Long save(Account account) {
//        return accountBox.put(account);
//    }
//
//    /**
//     * Retrieves all account entities from the database.
//     * 
//     * @return A list of all accounts
//     */
//    public List<Account> getAll() {
//        return accountBox.getAll();
//    }
//
//    /**
//     * Retrieves a account by its ID.
//     * 
//     * @param id The account ID
//     * @return An Optional containing the account if found, empty otherwise
//     */
//    public Optional<Account> getById(long id) {
//        Account account = accountBox.get(id);
//        return Optional.ofNullable(account);
//    }
//
//    /**
//     * Updates an existing account entity.
//     * 
//     * @param account The account with updated data
//     * @return true if the update was successful, false if the account doesn't exist
//     */
//    public boolean update(Account account) {
//        if (account.getId() == null || !accountBox.contains(account.getId())) {
//            return false;
//        }
//        accountBox.put(account);
//        return true;
//    }
//
//    /**
//     * Deletes a account by its ID.
//     * 
//     * @param id The account ID to delete
//     * @return true if the deletion was successful, false if the account doesn't
//     *         exist
//     */
//    public boolean deleteById(long id) {
//        if (!accountBox.contains(id)) {
//            return false;
//        }
//        accountBox.remove(id);
//        return true;
//    }
//
//    /**
//     * Counts the total number of accounts in the database.
//     * 
//     * @return The total count of accounts
//     */
//    public long count() {
//        return accountBox.count();
//    }
//
//    /**
//     * Checks if a account exists by its ID.
//     * 
//     * @param id The account ID to check
//     * @return true if the account exists, false otherwise
//     */
//    public boolean existsById(long id) {
//        return accountBox.contains(id);
//    }
//}
