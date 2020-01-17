-- insert admin
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (1, true, "New York", "admin@gmail.com", "Admin", "Admin", "admin", false, "USA", "Admin" , "admin", "admin");
-- insert urednik
insert into user(id, activated, city, email, first_name, last_name, password, reviewer, state, title, user_type, username) 
	values (2, true, "New York", "urednik@gmail.com", "Urednik", "Urednik", "urednik", false, "USA", "Urednik" , "urednik", "urednik");