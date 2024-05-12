package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Role;
import fr.istic.taa.jaxrs.domain.Status;

public class RoleDAO extends AbstractJpaDao<Long, Role> {
    public RoleDAO() {
        setClazz(Role.class);
    }

}
