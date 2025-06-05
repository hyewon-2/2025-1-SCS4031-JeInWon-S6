package JeInOne.WeGong.Util;

import JeInOne.WeGong.Repository.VenueRepository;
import JeInOne.WeGong.Service.VenueImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(0)
@Component
@RequiredArgsConstructor
public class VenueImportRunner implements CommandLineRunner {

    private final VenueImportService venueImportService;
    private final VenueRepository venueRepository;

    @Override
    public void run(String... args) throws Exception {
        if (venueRepository.count() == 0) {
            //venueImportService.importVenuesFromCSV();
        }
    }
}
