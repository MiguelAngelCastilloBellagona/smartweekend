INSERT INTO Adress VALUES (0, "no-responder@freaksparty.org", "e3MCq5>P2");

INSERT INTO EmailTemplate VALUES ( 0, "passwordRecover", ( SELECT Adress_id FROM Adress WHERE Adress_user =  "no-responder@freaksparty.org" ), "", "", "Recuperación contraseña plataforma Freak's Party webs",
 "La nueva contraseña para la cuenta #loginusuario es #nuevapas esta contraseña solo será válida durante #tiemporestante minutos, recuerda cambiarla.");
 
 
 INSERT INTO Event (Event_id,Event_name,Event_description,Event_num_participants,Event_minimunAge,Event_date_start,Event_date_end,Event_reg_date_open,Event_reg_date_close,Event_rules)
  VALUES (0, "NOMBRE Evento TEST", "DESCRIPCION Evento TEST", 5, 0, '2014-11-20 12:11:03', '2014-11-30 12:11:03', '2014-11-10 12:11:03', '2014-11-20 12:11:03',"REGALS REGLAS");
  