-- insert admin
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (1, true, "New York", "admin@gmail.com", "Admin", "Admin", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Admin" , "admin", "admin");
-- insert urednik
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (2, true, "New York", "devon.smith996@gmail.com", "Pera", "Peric", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Urednik" , "urednik", "urednik");
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (3, true, "New York", "devon.smith996@gmail.com", "Mika", "Mikic", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Urednik" , "urednik", "urednikR");
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (4, true, "New York", "devon.smith996@gmail.com", "Zika", "Zikic", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Urednik" , "urednik", "urednikH");
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (5, true, "New York", "devon.smith996@gmail.com", "Sima", "Simic", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Urednik" , "urednik", "urednikM");
-- insert recenzent
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (6, true, "New York", "albatraoz.seven@gmail.com", "Recenzent1", "Recenzent1", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Recenzent" , "recenzent", "recenzent1");
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (7, true, "New York", "albatraoz.seven@gmail.com", "Recenzent2", "Recenzent2", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Recenzent" , "recenzent", "recenzent2");
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (8, true, "New York", "albatraoz.seven@gmail.com", "Recenzent3", "Recenzent3", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Recenzent" , "recenzent", "recenzent3");
-- insert autor
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (9, true, "Novi Sad", "borkovac.dragan96@gmail.com", "Dragan", "Borkovac", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "Srbija", "Student" , "registrovan_korisnik", "borkovac");

-- insert casopis
insert into magazine (id, active, issn, open_access, title, main_editor_id)
	values (1, true, "3214-2352", false, "ComSIS", 2);
insert into magazine (id, active, issn, open_access, title, main_editor_id)
	values (2, true, "2134-2130", false, "Norma", 2);
insert into magazine (id, active, issn, open_access, title, main_editor_id)
	values (3, true, "6552-5463", true, "Filomat", 2);

-- insert naucna oblast
insert into magazine_scientific_area (id, name)
	values (1, "Racunarstvo");
insert into magazine_scientific_area (id, name)
	values (2, "Medicina");
insert into magazine_scientific_area (id, name)
	values (3, "Hemija");
insert into magazine_scientific_area (id, name)
	values (4, "Elektrotehnika");

-- insert interested_areas
insert into user_areas (user_id, scientific_area_id)
	values (3, 1);
insert into user_areas (user_id, scientific_area_id)
	values (4, 3);
insert into user_areas (user_id, scientific_area_id)
	values (5, 2);
insert into user_areas (user_id, scientific_area_id)
	values (6, 1);
insert into user_areas (user_id, scientific_area_id)
	values (6, 2);
insert into user_areas (user_id, scientific_area_id)
	values (6, 3);
insert into user_areas (user_id, scientific_area_id)
	values (7, 1);
insert into user_areas (user_id, scientific_area_id)
	values (7, 2);
insert into user_areas (user_id, scientific_area_id)
	values (8, 1);
	
-- insert magazine editors
insert into magazine_editor (magazine_id, user_id)
	values (1, 3);
insert into magazine_editor (magazine_id, user_id)
	values (1, 4);
insert into magazine_editor (magazine_id, user_id)
	values (1, 5);
	
-- insert magazine reviewers
insert into magazine_reviewer (magazine_id, user_id)
	values (1, 6);
insert into magazine_reviewer (magazine_id, user_id)
	values (1, 7);
insert into magazine_reviewer (magazine_id, user_id)
	values (1, 8);
	
-- insert recommendations
insert into recommendation (id, name)
	values (1, "Prihvatiti");
insert into recommendation (id, name)
	values (2, "Prihvatiti uz manje ispravke");
insert into recommendation (id, name)
	values (3, "Prihvatiti uz vece ispravke");
insert into recommendation (id, name)
	values (4, "Odbiti");

-- insert decisions
insert into decision (id, name)
	values (1, "Prihvacen");
insert into decision (id, name)
	values (2, "Prihvacen uz manju doradu");
insert into decision (id, name)
	values (3, "Uslovno prihvacen uz vece vece izmene");
insert into decision (id, name)
	values (4, "Odbijen");





