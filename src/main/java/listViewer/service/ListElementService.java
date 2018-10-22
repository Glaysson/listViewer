package listViewer.service;

import listViewer.entity.ListElement;

import java.util.List;
import java.util.Optional;

public interface ListElementService {

    void save(ListElement element);
    void delete(ListElement element);
    Optional<ListElement> findById(int id);
    List<ListElement> findAll();
    ListElement findByName(String name);
    Integer getPrevious(int id);
    Integer getNext(int id);

}
