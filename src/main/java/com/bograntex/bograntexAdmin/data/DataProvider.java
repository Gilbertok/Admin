package com.bograntex.bograntexAdmin.data;

import java.util.Collection;
import java.util.Date;

import com.bograntex.bograntexAdmin.domain.Transaction;
import com.bograntex.bograntexAdmin.domain.User;

/**
 * QuickTickets Dashboard backend API.
 */
public interface DataProvider {
    /**
     * @param count
     *            Number of transactions to fetch.
     * @return A Collection of most recent transactions.
     */
    Collection<Transaction> getRecentTransactions(int count);

    /**
     * @param userName
     * @param password
     * @return Authenticated used.
     */
    User authenticate(String userName, String password);

    /**
     * @return The number of unread notifications for the current user.
     */
    int getUnreadNotificationsCount();

    /**
     * @return The total summed up revenue of sold movie tickets
     */
    double getTotalSum();

    /**
     * @param startDate
     * @param endDate
     * @return A Collection of Transactions between the given start and end
     *         dates.
     */
    Collection<Transaction> getTransactionsBetween(Date startDate, Date endDate);
}
