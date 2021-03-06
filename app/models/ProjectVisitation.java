/**
 * Yobi, Project Hosting SW
 *
 * Copyright 2013 NAVER Corp.
 * http://yobi.io
 *
 * @Author Keesun Baik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 프로젝트 방문 기록.
 *
 * @author Keeun Baik
 */
@Entity
public class ProjectVisitation extends Model {

    private static final long serialVersionUID = 1L;

    public static final Finder <Long, ProjectVisitation> find = new Finder<>(Long.class, ProjectVisitation.class);

    @Id
    public Long id;

    @ManyToOne
    public Project project;

    @ManyToOne
    @JoinColumn(name = "recently_visited_projects_id")
    public RecentlyVisitedProjects recentlyVisitedProjects;

    @Temporal(TemporalType.TIMESTAMP)
    public Date visited;


    public static ProjectVisitation findBy(RecentlyVisitedProjects rvp, Project project) {
        return find.where()
                .eq("recentlyVisitedProjects", rvp)
                .eq("project", project)
                .findUnique();
    }

    public static List<ProjectVisitation> findRecentlyVisitedProjects(RecentlyVisitedProjects rvp, int size) {
        return find.where()
                .eq("recentlyVisitedProjects", rvp)
                .orderBy("visited desc")
                .setMaxRows(size)
                .findList();
    }
}
