package listViewer.service.Implementation;

import listViewer.dao.ListElementDAO;
import listViewer.entity.ListElement;
import listViewer.service.ListElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ListElementServiceImpl implements ListElementService {

    private final ListElementDAO dao;

    @Autowired
    public ListElementServiceImpl(ListElementDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(ListElement element) {
        dao.save(element);
    }

    @Override
    public void delete(ListElement element) {
        dao.delete(element);
    }

    @Override
    public Optional<ListElement> findById(int id) {
        return dao.findById(id);
    }

    @Override
    public List<ListElement> findAll() {
        return dao.findAll();
    }

    @Override
    public ListElement findByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public Integer getPrevious(int id) {
        return dao.getPrevious(id);
    }

    @Override
    public Integer getNext(int id) {
        return dao.getNext(id);
    }
}
