package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class InvoiceItemDaoJdbcImpl implements InvoiceItemDao {
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_INVOICEITEM_SQL =
            "insert into invoice_item (invoice_id, inventory_id, quantity, unit_price) values (?, ?, ?, ?)";

    private static final String SELECT_INVOICEITEM_SQL =
            "select * from invoice_item where invoice_item_id = ?";

    private static final String SELECT_ALL_INVOICEITEMS_SQL =
            "select * from invoice_item";

    private static final String SELECT_INVOICE_ITEMS_BY_INVOICE_ID =
            "select * from invoice_item where invoice_id=?";

    private static final String UPDATE_INVOICEITEM_SQL =
            "update invoice_item set invoice_id = ?, inventory_id = ?, quantity = ?, unit_price = ? where invoice_item_id = ?";

    private static final String DELETE_INVOICEITEM_SQL =
            "delete from invoice_item where invoice_item_id = ?";

    @Autowired
    public InvoiceItemDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Transactional
    @Override
    public InvoiceItem createInvoiceItem(InvoiceItem invoiceItem) {
        jdbcTemplate.update(
                INSERT_INVOICEITEM_SQL,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        invoiceItem.setInvoiceItemId(id);

        return invoiceItem;
    }
    @Override
    public InvoiceItem getInvoiceItem(int invoiceItemId) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_INVOICEITEM_SQL,
                    this::mapRowToInvoiceItem,
                    invoiceItemId);
        } catch (EmptyResultDataAccessException e) {
            // if there is no entry with the given id, just return null
            return null;
        }
    }

    @Override
    public List<InvoiceItem> getAllInvoiceItem() {
        return jdbcTemplate.query(
                SELECT_ALL_INVOICEITEMS_SQL,
                this::mapRowToInvoiceItem);
    }

    @Override
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(int invoiceId) {
        return jdbcTemplate.query(SELECT_INVOICE_ITEMS_BY_INVOICE_ID, this::mapRowToInvoiceItem, invoiceId);
    }

    @Override
    public void updateInvoiceItem(InvoiceItem invoiceItem) {
        jdbcTemplate.update(
                UPDATE_INVOICEITEM_SQL,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice(),
                invoiceItem.getInvoiceItemId());

    }

    @Override
    public void deleteInvoiceItem(int invoiceItemId) {
        jdbcTemplate.update(DELETE_INVOICEITEM_SQL, invoiceItemId);

    }

    //helper Method

    private InvoiceItem mapRowToInvoiceItem(ResultSet rs, int rowNum) throws SQLException {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(rs.getInt("invoice_item_id"));
        invoiceItem.setInvoiceId(rs.getInt("invoice_id"));
        invoiceItem.setInventoryId(rs.getInt("inventory_id"));
        invoiceItem.setQuantity(rs.getInt("quantity"));
        invoiceItem.setUnitPrice(rs.getBigDecimal("unit_price"));
        return invoiceItem;

    }
}
