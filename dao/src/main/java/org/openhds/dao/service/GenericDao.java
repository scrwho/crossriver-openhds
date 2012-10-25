package org.openhds.dao.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

/**
 * A generic implementation of a Dao that simplifies Dao/BaseDaoImpl This class differs from the Dao/BaseDaoImpl in that
 * it is not have generic parameters. Instead it uses generic methods so that only one instance of this class needs to
 * be created and can be shared by any entity
 * 
 * @author dave
 * 
 */
public interface GenericDao {

    interface OrderProperty {

        String getPropertyName();

        boolean isAscending();
    }

    static class OrderPropertyBuilder {
        public static OrderProperty build(final String propertyName, final boolean isAscending) {
            return new OrderProperty() {
                @Override
                public String getPropertyName() {
                    return propertyName;
                }

                @Override
                public boolean isAscending() {
                    return isAscending;
                }
            };
        }
    }

    interface ValueProperty {

        String getPropertyName();

        Object getValue();
    }

    static class ValuePropertyBuilder {
        public static ValueProperty build(final String propertyName, final Object value) {
            return new ValueProperty() {
                @Override
                public String getPropertyName() {
                    return propertyName;
                }

                @Override
                public Object getValue() {
                    return value;
                }
            };
        }
    }

    <T> String create(T newInstance);

    <T> T read(Class<T> entityType, String id);

    <T> void refresh(T entityItem);

    <T> void update(T transientObject);

    <T> void delete(T persistentObject);

    <T> T merge(T entityItem);

    <T> List<T> findAll(Class<T> entityType, boolean filterDeleted);

    <T> List<T> findAllDistinct(Class<T> entityType);

    <T> List<T> findAllWithOrder(Class<T> entityType, OrderProperty... orderProps);

    <T> T findByProperty(Class<T> entityType, String propertyName, Object value);

    <T> T findByProperty(Class<T> entityType, String propertyName, Object value, boolean filterDeleted);

    <T> T findByMultiProperty(Class<T> entityType, ValueProperty... properties);

    <T> List<T> findListByMultiProperty(Class<T> entityType, ValueProperty... properties);

    <T> List<T> findListByProperty(Class<T> entityType, String propertyName, Object value);

    <T> List<T> findListByProperty(Class<T> entityType, String propertyName, Object value, boolean filterDeleted);

    <T> List<T> findListByPropertyWithOrder(Class<T> entityType, String propertyName, Object value,
            OrderProperty... orderProps);

    <T> T findUniqueByPropertyWithOrder(Class<T> entityType, String propertyName, Object value, String orderByCol,
            boolean ascending);

    void clear();

    <T> long getTotalCount(Class<T> entityType);

    <T> List<T> findByExample(Class<T> entityType, T exampleInstance, String... excludeProperty);

    <T> Map<T, T> getClassMetaData();

    Session getSession();

    <T> List<T> findListByPropertyPrefix(Class<T> entityType, String propertyName, String prefix, int limit,
            boolean ascendingOrder, boolean filterDeleted);

    <T, S> T findUniqueByInPropertyWithOrder(Class<T> entityType, String property, Object value, String inProperty,
            List<S> inValues, OrderProperty orderProp, boolean filterDeleted);
}
