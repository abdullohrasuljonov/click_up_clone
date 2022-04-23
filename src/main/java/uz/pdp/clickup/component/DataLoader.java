package uz.pdp.clickup.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.clickup.entity.ClickApps;
import uz.pdp.clickup.entity.Priority;
import uz.pdp.clickup.entity.View;
import uz.pdp.clickup.repository.ClickAppsRepository;
import uz.pdp.clickup.repository.PriorityRepository;
import uz.pdp.clickup.repository.ViewRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    ViewRepository viewRepository;

    @Autowired
    ClickAppsRepository clickAppsRepository;

    @Autowired
    PriorityRepository priorityRepository;

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            List<ClickApps> apps = new ArrayList<>();
            apps.add(new ClickApps("Priority", null));
            apps.add(new ClickApps("Sprints", null));
            apps.add(new ClickApps("Email", null));
            apps.add(new ClickApps("Tags", null));
            apps.add(new ClickApps("Custom fields", null));
            apps.add(new ClickApps("Multiple Assignees", null));
            apps.add(new ClickApps("Time Tracking", null));

            clickAppsRepository.saveAll(apps);

            List<View> views = new ArrayList<>();

            views.add(new View("List", null));
            views.add(new View("Board", null));
            views.add(new View("Calendar", null));
            views.add(new View("Map", null));
            views.add(new View("Activity", null));
            views.add(new View("Box", null));
            views.add(new View("Timeline", null));

            viewRepository.saveAll(views);

            List<Priority> priorities = new ArrayList<>();
            priorities.add(new Priority("Urgent", null));
            priorities.add(new Priority("High", null));
            priorities.add(new Priority("Normal", null));
            priorities.add(new Priority("Low", null));

            priorityRepository.saveAll(priorities);
        }
    }
}
