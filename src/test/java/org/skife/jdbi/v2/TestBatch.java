/*
 * Copyright 2004-2007 Brian McCallister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.skife.jdbi.v2;

import org.skife.jdbi.v2.util.StringMapper;

import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 *
 */
public class TestBatch extends DBITestCase
{
    public void testBasics() throws Exception
    {
        Handle h = this.openHandle();

        Batch b = h.createBatch();
        b.add("insert into something (id, name) values (0, 'Keith')");
        b.add("insert into something (id, name) values (1, 'Eric')");
        b.add("insert into something (id, name) values (2, 'Brian')");
        b.execute();

        List<Something> r = h.createQuery("select * from something order by id").map(Something.class).list();
        assertEquals(3, r.size());
    }

    public void testApiExploration() throws Exception
    {
        Handle h = this.openHandle();
        List<String> rs = h.createQuery("select name from something where id = :id")
                           .bind("id", 7)
                           .map(StringMapper.FIRST)
                           .list();

        List<Set<String>> rs2 = h.createBatchQuery("select name from something where id = :id")
                                  .bind("id", asList(1, 2, 3, 4, 5))
                                  .map(StringMapper.FIRST)
                                  .list(Set.class);

    }

}
