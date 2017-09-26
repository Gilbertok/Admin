package com.bograntex.bograntexAdmin.dummy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.bograntex.bograntexAdmin.data.DataProvider;
import com.bograntex.bograntexAdmin.domain.Transaction;
import com.bograntex.bograntexAdmin.domain.User;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * A dummy implementation for the backend API.
 */
public class DummyDataProvider implements DataProvider {

    /* List of countries and cities for them */
    private static Date lastDataUpdate;
    private static Multimap<Long, Transaction> transactions;

    /**
     * Initialize the data for this application.
     */
    public DummyDataProvider() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        if (lastDataUpdate == null || lastDataUpdate.before(cal.getTime())) {
            refreshStaticData();
            lastDataUpdate = new Date();
        }
    }

    private void refreshStaticData() {
    }


    /**
     * =========================================================================
     * Countries, cities, theaters and rooms
     * =========================================================================
     */

    static List<String> theaters = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("Threater 1");
            add("Threater 2");
            add("Threater 3");
            add("Threater 4");
            add("Threater 5");
            add("Threater 6");
        }
    };

    static List<String> rooms = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("Room 1");
            add("Room 2");
            add("Room 3");
            add("Room 4");
            add("Room 5");
            add("Room 6");
        }
    };

    @Override
    public User authenticate(String userName, String password) {
        User user = new User();
        user.setFirstName(DummyDataGenerator.randomFirstName());
        user.setLastName(DummyDataGenerator.randomLastName());
        user.setRole("admin");
        String email = user.getFirstName().toLowerCase() + "."+ user.getLastName().toLowerCase() + "@"+ DummyDataGenerator.randomCompanyName().toLowerCase() + ".com";
        user.setEmail(email.replaceAll(" ", ""));
        user.setLocation(DummyDataGenerator.randomWord(5, true));
        user.setBio("Quis aute iure reprehenderit in voluptate velit esse."
                + "Cras mattis iudicium purus sit amet fermentum.");
        return user;
    }

    @Override
    public Collection<Transaction> getRecentTransactions(int count) {
        List<Transaction> orderedTransactions = Lists.newArrayList(transactions.values());
        Collections.sort(orderedTransactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o2.getTime().compareTo(o1.getTime());
            }
        });
        return orderedTransactions.subList(0,
                Math.min(count, transactions.values().size() - 1));
    }

    @Override
    public double getTotalSum() {
        double result = 0;
        for (Transaction transaction : transactions.values()) {
            result += transaction.getPrice();
        }
        return result;
    }

    @Override
    public Collection<Transaction> getTransactionsBetween(final Date startDate, final Date endDate) {
        return Collections2.filter(transactions.values(), new Predicate<Transaction>() {
            @Override
            public boolean apply(Transaction input) {
                return !input.getTime().before(startDate) && !input.getTime().after(endDate);
            }
        });
    }

	@Override
	public int getUnreadNotificationsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
