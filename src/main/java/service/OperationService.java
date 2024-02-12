package service;

import dao.OperationDao;
import dao.impl.OperationDaoImpl;

import entity.Operation;
import exeption.EntityNotFoundException;

import java.util.List;

public class OperationService {
    private final OperationDao operationDao = new OperationDaoImpl(Operation.class);

    public OperationService() {
    }

    public Operation findOperationByID(long id) {
        return operationDao.findByID(id)
                .orElseThrow(() -> new EntityNotFoundException("Operation with " + id + " not found"));
    }

    public List<Operation> findAllOperation() {
        return operationDao.findAll();
    }

    public void saveOperation(Operation operation) {
        operationDao.save(operation);
    }

    public void updateOperation(Operation operation) {
        operationDao.update(operation);
    }

    public void deleteOperation(Operation operation) {
        System.out.println("Removing an operation occurs using the method accountService.deleteOperation");
    }
}
