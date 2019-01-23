--DROP VIEW parking.PARKING_SPACE_VIEW;

CREATE OR REPLACE VIEW parking.PARKING_SPACE_VIEW AS
       SELECT
              ps.id as PARKING_SPACE_ID,
              ps.status,
              vr.registration,
              vr.start_date_time as OCCUPIED_FROM
       FROM parking.parking_space ps
                   LEFT JOIN ( SELECT * FROM  parking.vehicle_registry WHERE is_parked = true) as vr on vr.parking_space_id=ps.id;


