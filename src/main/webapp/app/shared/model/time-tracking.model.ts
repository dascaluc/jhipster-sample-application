import { Moment } from 'moment';
import { IDoor } from 'app/shared/model/door.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { AccesType } from 'app/shared/model/enumerations/acces-type.model';

export interface ITimeTracking {
  id?: number;
  date?: Moment;
  accessTypee?: AccesType;
  description?: string;
  door?: IDoor;
  employee?: IEmployee;
}

export const defaultValue: Readonly<ITimeTracking> = {};
