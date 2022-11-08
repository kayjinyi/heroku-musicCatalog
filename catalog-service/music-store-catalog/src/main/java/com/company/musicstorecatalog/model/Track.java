package com.company.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Integer id;
    private String title;

    @Column(name = "album_id")
    private Integer albumId;

    @Column(name = "run_time")
    private BigDecimal runTime;

    public Track() {
    }

    public Track(Integer id, String title, Integer albumId, BigDecimal runTime) {
        this.id = id;
        this.title = title;
        this.albumId = albumId;
        this.runTime = runTime;
    }

    public Track(String title, Integer albumId, BigDecimal runTime) {
        this.title = title;
        this.albumId = albumId;
        this.runTime = runTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public BigDecimal getRunTime() {
        return runTime;
    }

    public void setRunTime(BigDecimal runTime) {
        this.runTime = runTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(id, track.id) && Objects.equals(title, track.title) && Objects.equals(albumId, track.albumId) && Objects.equals(runTime, track.runTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, albumId, runTime);
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", albumId=" + albumId +
                ", runTime=" + runTime +
                '}';
    }
}
