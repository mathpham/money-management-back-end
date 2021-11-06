package net.money.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.money.models.ExpenseInfo;
import net.money.models.ExpenseType;
import net.money.models.Family;
import net.money.models.Store;
import net.money.models.User;

public interface ExpenseInfoRepository extends JpaRepository<ExpenseInfo, Long>{
	List<ExpenseInfo> findByFamilyAndUser(Family family, User user);
	
	List<ExpenseInfo> findByFamily(Family family);
	
	List<ExpenseInfo> findByUser(User user);
	
	Optional<ExpenseInfo> findById(Long id);
	
	@Query(value = "From ExpenseInfo where datetime BETWEEN :startDate AND :endDate")
	public List<ExpenseInfo> getAllBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate);

	@Query(value = "select e from ExpenseInfo e where e.family=:family and e.datetime between :startDate and :endDate")
	public List<ExpenseInfo> getExpenseInfoForFamily(@Param("family")Family family,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	@Query(value = "select e from ExpenseInfo e where e.user=:user and e.datetime between :startDate and :endDate")
	public List<ExpenseInfo> getExpenseInfoForUser(@Param("user")User user,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	
	@Query(value = "select e from ExpenseInfo e where "
			+ "(e.family = :family)"
			+ "and (e.expenseType = :expenseType or :expenseType is null)"
			+ "and (e.store = :store or :store is null)"
			+ "and (e.user = :member or :member is null) "
			+ "and (e.amountOfMoney >= :lowMoney and e.amountOfMoney < :highMoney or :lowMoney is null or :highMoney is null)"
			+ "and (e.datetime between :startDate and :endDate)")
	public List<ExpenseInfo> getExpenseInfoForFilter(@Param("family") Family family,
												@Param("expenseType")ExpenseType expenseType,
												@Param("store")Store store,
												@Param("member")User member,
												@Param("lowMoney")Long lowMoney,
												@Param("highMoney")Long highMoney,
												@Param("startDate")Date startDate,
												@Param("endDate")Date endDate);
}
