package uk.co.n3fs.mc.haroldplugin.storage.backend;

import uk.co.n3fs.mc.haroldplugin.platform.User;
import uk.co.n3fs.mc.haroldplugin.storage.Punishment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A storage backend for Harold data.
 */
public interface Backend {

    void createPunishment(Punishment punishment) throws Exception;

    void updatePunishment(Punishment punishment) throws Exception;

    List<Punishment> getAllPunishments();

    default List<Punishment> getPunishmentsFor(User user) {
        return getAllPunishments().stream()
            .filter(i -> i.getOffender().getUniqueId().equals(user.getUniqueId()))
            .collect(Collectors.toList());
    }

    default Optional<Punishment> getPunishment(UUID uniqueId) {
        return getAllPunishments().stream()
            .filter(i -> i.getUniqueId().equals(uniqueId))
            .findAny();
    }

    void removePunishment(Punishment punishment) throws Exception;

}
