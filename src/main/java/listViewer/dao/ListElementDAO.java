package listViewer.dao;

import listViewer.entity.ListElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListElementDAO extends JpaRepository<ListElement, Integer> {
    @Query("from ListElement p where p.text = :name group by p.id")
    ListElement findByName(@Param("name") String name);
    @Query("SELECT MAX(id) FROM ListElement WHERE id < :_id")
    Integer getPrevious(@Param("_id") int id);
    @Query("SELECT MIN(id) FROM ListElement WHERE id > :_id")
    Integer getNext(@Param("_id") int id);
}
