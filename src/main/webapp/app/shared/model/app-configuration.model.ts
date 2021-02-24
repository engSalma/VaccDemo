import { DataType } from 'app/shared/model/enumerations/data-type.model';

export interface IAppConfiguration {
  id?: string;
  key?: string;
  dataType?: DataType;
  pattern?: string;
  value?: string;
}

export class AppConfiguration implements IAppConfiguration {
  constructor(public id?: string, public key?: string, public dataType?: DataType, public pattern?: string, public value?: string) {}
}
