package JeInOne.WeGong.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class BusinessOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    private String contact;
    private String link;
    private String account;

    @OneToMany(mappedBy = "businessOwner", cascade = CascadeType.ALL)
    private List<Venue> venues = new ArrayList<>();

    public BusinessOwner(String name, String password, String contact, String account) {
        this.name = name;
        this.password = password;
        this.contact = contact;
        this.account = account;
    }
}
