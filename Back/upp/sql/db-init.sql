-- insert admin
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (1, true, "New York", "admin@gmail.com", "Admin", "Admin", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Admin" , "admin", "admin");
-- insert urednik
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (2, true, "New York", "urednik@gmail.com", "Urednik1", "Urednik1", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Urednik" , "urednik", "urednik1");
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (3, true, "New York", "urednik@gmail.com", "Urednik2", "Urednik2", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Urednik" , "urednik", "urednik2");
-- insert recenzent
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (4, true, "New York", "recenzent@gmail.com", "Recenzent1", "Recenzent1", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Recenzent" , "recenzent", "recenzent1");
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (5, true, "New York", "recenzent@gmail.com", "Recenzent2", "Recenzent2", "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", false, "USA", "Recenzent" , "recenzent", "recenzent2");