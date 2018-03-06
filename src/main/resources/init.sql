/*select * from ANSWER;


create or replace view survey_voters as  
SELECT SURVEY_ID,sum(1) TOTAL ,sum(case when REPLYER_POSITION like '技术类' then 1 else 0 end) engineer,sum(case when REPLYER_POSITION like '管理类' then 1 else 0 end) manager,
sum(case when REPLYER_POSITION like '营销类' then 1 else 0 end)  sale, sum(case when REPLYER_POSITION like '设计类' then 1 else 0 end) designer
FROM ANSWER  group by SURVEY_ID;


create or replace view survey_answer_all_info as
select SURVEY_ID,REPLYER_POSITION  ,ai.IMAGE_ID,IMAGE_NAME,THUMB_URL,BRAND,i.MODUEL model,
STYLE_KEYWORD,TEXTURE,YEAR from answer a left join ANSWER_IMAGE  ai on a.answer_id = ai.answer_id left join image i on ai.image_id = i.image_id where length(ai.IMAGE_ID)<9;


create or replace view survey_Style_MAtrix as 
select SURVEY_ID, 
sum(case when STYLE_KEYWORD like '%现代%' then 1 else 0 end)  x1,
sum(case when STYLE_KEYWORD like '%传统%' then 1 else 0 end)  x2,
sum(case when STYLE_KEYWORD like '%圆润%' then 1 else 0 end) y1,
sum(case when STYLE_KEYWORD like '%硬朗%' then 1 else 0 end) y2,
sum(case when STYLE_KEYWORD like '%简洁%' then 1 else 0 end) z1,
sum(case when STYLE_KEYWORD like '%复杂%' then 1 else 0 end) z2
 from SURVEY_ANSWER_ALL_INFO group by SURVEY_ID;


create or replace view SURVey_Candidates as 
select 
SURVEY_ID,IMAGE_ID ,sum(1) TOTAL ,sum(case when REPLYER_POSITION like '技术类' then 1 else 0 end) engineer,sum(case when REPLYER_POSITION like '管理类' then 1 else 0 end) manager,
sum(case when REPLYER_POSITION like '营销类' then 1 else 0 end)  sale, sum(case when REPLYER_POSITION like '设计类' then 1 else 0 end) designer,
max(brand) brand,max(model) model, max(year) year,max(style_keyword) style_keyword,max(texture) texture, max(THUMB_URL) THUMB_URL
from SURVEY_ANSWER_ALL_INFO  group by SURVEY_ID,IMAGE_ID  order by TOTAL;
*/
--truncate table CREATOR;
--insert into CREATOR(EMAIL,PWD) values('niel@123.com','123');

--create or replace view SERVEY_IMAGE as SELECT a.SURVEY_ID,a.ANSWER_ID,im.IMAGE_ID,im.IMAGE_NAME,im.IMAGE_DESC FROM ANSWER a left join ANSWER_IMAGE i  on a.ANSWER_ID =i.ANSWER_ID  left join IMAGE im on i.Image_id = im.IMAGE_ID where IMAGE_TYPE like 'WHOLE';
--create or replace view SURVEY_IMAGE_RESULT as select SURVEY_ID,IMAGE_ID,count(*) cnt,max(IMAGE_DESC) DESC from SERVEY_IMAGE  group by SURVEY_ID,IMAGE_ID order by cnt desc;


