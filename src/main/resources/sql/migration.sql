-- tbl_members
ALTER TABLE tbl_members
  ALTER COLUMN image TYPE JSON USING image::json;

-- tbl_store
ALTER TABLE tbl_store
  ALTER COLUMN businessHours TYPE JSON USING businessHours::json,
  ALTER COLUMN regularHolidays TYPE JSON USING regularHolidays::json,
  ALTER COLUMN temporaryHolidays TYPE JSON USING temporaryHolidays::json,
  ALTER COLUMN image TYPE JSON USING image::json;

-- tbl_placement
ALTER TABLE tbl_placement
  ALTER COLUMN layout TYPE JSON USING layout::json;

-- tbl_menu
ALTER TABLE tbl_menu
  ALTER COLUMN image TYPE JSON USING image::json;

-- tbl_reservation
ALTER TABLE tbl_reservation
  ALTER COLUMN menu TYPE JSON USING menu::json;

-- tbl_review
ALTER TABLE tbl_review
  ALTER COLUMN image TYPE JSON USING image::json;