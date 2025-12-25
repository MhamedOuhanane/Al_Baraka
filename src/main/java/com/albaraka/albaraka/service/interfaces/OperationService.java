package com.albaraka.albaraka.service.interfaces;

import com.albaraka.albaraka.model.dto.operation.OperationCreateDTO;
import com.albaraka.albaraka.model.dto.operation.OperationDTO;
import com.albaraka.albaraka.model.dto.operation.OperationFindDTO;
import com.albaraka.albaraka.model.enums.OperationStatus;

import java.util.List;
import java.util.UUID;

public interface OperationService {
    OperationFindDTO create(OperationCreateDTO dto);
    OperationFindDTO updateStatus(UUID uuid, OperationStatus status);
    List<OperationDTO> findAll();
    List<OperationDTO> findAllByClient(UUID uuid);
}
