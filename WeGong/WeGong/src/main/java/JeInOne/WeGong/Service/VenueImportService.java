package JeInOne.WeGong.Service;

import JeInOne.WeGong.Entity.BusinessOwner;
import JeInOne.WeGong.Entity.Venue;
import JeInOne.WeGong.Enums.musicGenre;
import JeInOne.WeGong.Repository.BusinessOwnerRepository;
import JeInOne.WeGong.Repository.VenueRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VenueImportService {

    private final VenueRepository venueRepository;
    private final BusinessOwnerRepository businessOwnerRepository;

    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void importVenuesFromCSV() throws Exception {
        var resource = new ClassPathResource("Mapo_Venues.csv");
        try (var inputStream = resource.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            if (reader.read() != '\ufeff') {
                reader.reset();
            }

            List<BusinessOwner> owners = businessOwnerRepository.findAll();
            if (owners.isEmpty()) {
                insertDummyBusinessOwners();
                owners = businessOwnerRepository.findAll();
            }

            if (venueRepository.count() == 0) {
                try (var csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
                    for (CSVRecord record : csvParser) {
                        Venue venue = Venue.builder()
                                .name(record.get("시설명").trim())
                                .capacity(parseIntOrNull(record.get("총객석수")))
                                .city(record.get("시(도)").trim())
                                .district(record.get("구(군)").trim())
                                .address(record.get("주소").trim())
                                .rental(random.nextBoolean())
                                .eventhosting(random.nextBoolean())
                                .isIndoor(random.nextBoolean())
                                .contact("010-" + String.format("%04d-%04d", random.nextInt(10000), random.nextInt(10000)))
                                .siteLink("https://example.com/venue/" + UUID.randomUUID())
                                .musicGenres(randomGenreSet())
                                .businessOwner(getRandomOwner(owners))
                                .build();

                        venueRepository.save(venue);

                    }
                }
            }
        }
    }

    private Integer parseIntOrNull(String value) {

        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Set<musicGenre> randomGenreSet() {
        musicGenre[] genres = musicGenre.values();
        Set<musicGenre> set = new HashSet<>();
        int count = random.nextInt(3) + 1;
        while (set.size() < count) {
            set.add(genres[random.nextInt(genres.length)]);
        }
        return set;
    }

    private BusinessOwner getRandomOwner(List<BusinessOwner> owners) {
        return owners.get(random.nextInt(owners.size()));
    }

    public void insertDummyBusinessOwners() {
        if (businessOwnerRepository.count() == 0) {
            for (int i = 1; i <= 5; i++) {
                BusinessOwner owner = BusinessOwner.builder()
                        .name("사업자" + i)
                        .password(passwordEncoder.encode("password" + i))
                        .contact("010-" + String.format("%04d-%04d", random.nextInt(10000), random.nextInt(10000)))
                        .link("https://example.com/owner/" + UUID.randomUUID())
                        .account("우리은행 " + String.format("%04d-%04d-%04d", random.nextInt(10000), random.nextInt(1000), random.nextInt(100000)))
                        .build();
                businessOwnerRepository.save(owner);
            }
        }
    }
}
