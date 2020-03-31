package org.themselves.alber.repository.projection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjection{
    Object getNickname();
    Object getUrl();
}
