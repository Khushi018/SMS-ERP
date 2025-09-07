package com.aristiec.schoolmanagementsystem.transport.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.transport.dto.StoppageDto;
import com.aristiec.schoolmanagementsystem.transport.modal.Route;
import com.aristiec.schoolmanagementsystem.transport.modal.Stoppage;
import com.aristiec.schoolmanagementsystem.transport.modal.StoppageGroup;
import com.aristiec.schoolmanagementsystem.transport.repository.RouteRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.StoppageGroupRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.StoppageRepository;
import com.aristiec.schoolmanagementsystem.transport.service.StoppageService;

import jakarta.transaction.Transactional;

@Service
public class StoppageServiceImpl implements StoppageService {

    @Autowired
    private StoppageRepository repo;

    @Autowired
    private RouteRepository routeRepo;

    @Autowired
    private StoppageGroupRepository stoppageGroupRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public StoppageDto create(StoppageDto dto) {
        Route route = routeRepo.findById(dto.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + dto.getRouteId()));

        Stoppage stoppage = mapper.map(dto, Stoppage.class);
        stoppage.setRoute(route);

        stoppage = repo.save(stoppage);
        return mapper.map(stoppage, StoppageDto.class);
    }

    @Override
    @Transactional
    public List<StoppageDto> createGroupedStoppages(String groupName, List<StoppageDto> dtos) {
        StoppageGroup group = new StoppageGroup();
        group.setGroupName(groupName);
        stoppageGroupRepo.save(group);

        List<Stoppage> stoppages = new ArrayList<>();
        for (StoppageDto dto : dtos) {
            Route route = routeRepo.findById(dto.getRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found"));
            Stoppage stoppage = mapper.map(dto, Stoppage.class);
            stoppage.setRoute(route);
            stoppage.setGroup(group);
            stoppages.add(stoppage);
        }

        return repo.saveAll(stoppages).stream()
                .map(s -> mapper.map(s, StoppageDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StoppageDto> getByRoute(Long routeId) {
        return repo.findByRouteId(routeId).stream()
                .map(s -> mapper.map(s, StoppageDto.class))
                .toList();
    }

    @Override
    public List<StoppageDto> getAllStoppages() {
        return repo.findAll().stream()
                .map(s -> mapper.map(s, StoppageDto.class))
                .toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}