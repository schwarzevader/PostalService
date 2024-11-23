package com.example.postservice.service.imp;

import com.example.postservice.domain.PostOffice;
import com.example.postservice.domain.RouteDistanceToOffice;
import com.example.postservice.repository.PostOfficeRepo;
import com.example.postservice.service.interfaces.ShortestPathAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("dijkstraAlgorithm")
public class DijkstraAlgorithm implements ShortestPathAlgorithm {

    private PostOfficeRepo postOfficeRepo;



    @Autowired
    public DijkstraAlgorithm(PostOfficeRepo postOfficeRepo) {
        this.postOfficeRepo = postOfficeRepo;
    }

    public  List<PostOffice> findShortestPath(Long startId, Long goalId) {

        PostOffice start = this.postOfficeRepo.findPostOfficeByIdWithAllCarsAndRout(startId);
        PostOffice goal =this.postOfficeRepo.findPostOfficeByIdWithAllCarsAndRout(goalId);

        // Приоритетная очередь для обработки узлов с минимальным расстоянием
        PriorityQueue<PostOffice> queue = new PriorityQueue<>();
        // Карта для хранения расстояния от стартового узла до текущего узла
        Map<PostOffice, Double> distances = new HashMap<>();
        // Карта для хранения предков, чтобы восстановить путь
        Map<PostOffice, PostOffice> previous = new HashMap<>();

        // Инициализация
        start.setDistance(0);
        queue.add(start);
        distances.put(start, 0.0);

        while (!queue.isEmpty()) {
            PostOffice current = queue.poll(); // Извлекаем узел с минимальным расстоянием

            // Если текущий узел — целевой, завершить
            if (current.equals(goal)) {
                break;
            }

            // Обрабатываем всех соседей текущего узла
            for (RouteDistanceToOffice edge : current.getPostalCars().stream().flatMap(car -> car.getToPostOffices().stream()).toList()) {
                PostOffice neighbor = edge.getToPostOffice();
                double newDist = current.getDistance() + edge.getDistance();

                // Если найдено более короткое расстояние до соседа
                if (newDist < neighbor.getDistance()) {
                    queue.remove(neighbor); // Удаляем соседа из очереди, если он там есть
                    neighbor.setDistance(newDist); // Обновляем расстояние
                    previous.put(neighbor, current); // Устанавливаем предка
                    distances.put(neighbor, newDist);
                    queue.add(neighbor); // Добавляем соседа обратно в очередь
                }
            }
        }

        // Восстановление пути от goal к start
        List<PostOffice> path = new ArrayList<>();
        PostOffice step = goal;

        if (!previous.containsKey(goal)) {
            return path; // Путь не найден
        }

        while (step != null) {
            path.add(step);
            step = previous.get(step);
        }

        // Путь был восстановлен в обратном порядке, нужно его развернуть
        Collections.reverse(path);
        return path;
    }
}
