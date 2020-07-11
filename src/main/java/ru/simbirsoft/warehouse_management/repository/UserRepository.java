package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.repository.CrudRepository;
import ru.simbirsoft.warehouse_management.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
