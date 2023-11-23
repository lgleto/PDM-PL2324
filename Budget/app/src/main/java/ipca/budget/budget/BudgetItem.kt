package ipca.budget.budget

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import java.util.Date

@Entity
data class BudgetItem (
    @PrimaryKey
    val id : String,
    val description : String,
    val value : Double,
    val date : Date
)

@Dao
interface BudgetItemDao {
    @Query("SELECT * FROM budgetitem")
    fun getAll(): LiveData<List<BudgetItem>>

    @Query("SELECT * FROM budgetitem WHERE id = :id")
    fun loadById(id: String): BudgetItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(budgetItem: BudgetItem)

    @Delete
    fun delete(budgetItem: BudgetItem)
}