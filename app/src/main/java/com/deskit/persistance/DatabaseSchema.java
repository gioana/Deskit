package com.deskit.persistance;

public class DatabaseSchema {

    static final class CityTable {
        static final String NAME = "cities";

        static final class Columns {
            static final String ID = "id";

            static final String CITY_NAME = "city_name";
        }
    }

    public static final class UniversityTable {
        public static final String NAME = "universities";

        public static final class Columns {
            public static final String ID = "id";

            public static final String CITY_ID = "city_id";

            public static final String UNIVERSITY_NAME = "university_name";
        }
    }

    public static final class FacultyTable {
        public static final String NAME = "faculties";

        public static final class Columns {
            public static final String ID = "id";

            public static final String UNIVERSITY_ID = "university_id";

            public static final String FACULTY_NAME = "faculty_name";
        }
    }

    public static final class SubjectTable {
        public static final String NAME = "subjects";

        public static final class Columns {
            public static final String ID = "id";

            public static final String FACULTY_ID = "faculty_id";

            public static final String SUBJECT_NAME = "subject_name";
        }
    }
}
