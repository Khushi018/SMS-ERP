package com.aristiec.schoolmanagementsystem.transport.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.transport.dto.RouteTimeTableDto;
import com.aristiec.schoolmanagementsystem.transport.modal.Route;
import com.aristiec.schoolmanagementsystem.transport.modal.RouteTimeTable;
import com.aristiec.schoolmanagementsystem.transport.modal.Stoppage;
import com.aristiec.schoolmanagementsystem.transport.repository.RouteTimeTableRepository;
import com.aristiec.schoolmanagementsystem.transport.service.RouteTimeTableService;

@Service
public class RouteTimeTableServiceImpl implements RouteTimeTableService {

    @Autowired
    private RouteTimeTableRepository repo;
    @Autowired
    private ModelMapper mapper;

    @Override
    public RouteTimeTableDto create(RouteTimeTableDto dto) {
        RouteTimeTable rtt = mapper.map(dto, RouteTimeTable.class);

        Route route = new Route();
        route.setId(dto.getRouteId());
        rtt.setRoute(route);

        Stoppage stoppage = new Stoppage();
        stoppage.setId(dto.getStoppageId());
        rtt.setStoppage(stoppage);

        rtt = repo.save(rtt);
        return mapper.map(rtt, RouteTimeTableDto.class);
    }

    @Override
    public List<RouteTimeTableDto> getByRoute(Long routeId) {
        return repo.findByRouteId(routeId).stream()
                .map(r -> mapper.map(r, RouteTimeTableDto.class))
                .toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
