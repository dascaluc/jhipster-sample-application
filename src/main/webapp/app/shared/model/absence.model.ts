import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IAbsenceType } from 'app/shared/model/absence-type.model';

export interface IAbsence {
  id?: number;
  from?: Moment;
  to?: Moment;
  phoneNumber?: string;
  motivation?: string;
  employee?: IEmployee;
  type?: IAbsenceType;
}

export const defaultValue: Readonly<IAbsence> = {};
