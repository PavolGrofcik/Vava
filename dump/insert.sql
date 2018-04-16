\c javaprojekt

insert into customer(first_name,last_name,birth,sex,tel_number,city,address) values(
					'Pavol', 'Grofcík', '05-06-1997','M','0917469415', 'Zákamenné','Mrzáčka 777');
insert into account(customer_id,cash,username,password) values(
					1,1000,'Paul','HelloWorld');
insert into event(location,E_start,length,height,insurance) values(
					'Novoť', '20-1-2019',20,0.453,false);