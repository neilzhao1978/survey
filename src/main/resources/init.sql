select * from ANSWER;
<<<<<<< HEAD
--truncate table CREATOR;
--insert into CREATOR(EMAIL,PWD) values('niel@123.com','123');
=======
--insert into creator(email,pwd) values('niel@123.com','123')
>>>>>>> b25add4acfc38b7c9eedafec84173d91ae182ac5

--create or replace view SERVEY_IMAGE as SELECT a.SURVEY_ID,a.ANSWER_ID,im.IMAGE_ID,im.IMAGE_NAME,im.IMAGE_DESC FROM ANSWER a left join ANSWER_IMAGE i  on a.ANSWER_ID =i.ANSWER_ID  left join IMAGE im on i.Image_id = im.IMAGE_ID where IMAGE_TYPE like 'WHOLE';

--create or replace view SURVEY_IMAGE_RESULT as select SURVEY_ID,IMAGE_ID,count(*) cnt,max(IMAGE_DESC) DESC from SERVEY_IMAGE  group by SURVEY_ID,IMAGE_ID order by cnt desc;