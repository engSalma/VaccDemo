import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAppConfiguration } from 'app/shared/model/app-configuration.model';

type EntityResponseType = HttpResponse<IAppConfiguration>;
type EntityArrayResponseType = HttpResponse<IAppConfiguration[]>;

@Injectable({ providedIn: 'root' })
export class AppConfigurationService {
  public resourceUrl = SERVER_API_URL + 'api/app-configurations';

  constructor(protected http: HttpClient) {}

  create(appConfiguration: IAppConfiguration): Observable<EntityResponseType> {
    return this.http.post<IAppConfiguration>(this.resourceUrl, appConfiguration, { observe: 'response' });
  }

  update(appConfiguration: IAppConfiguration): Observable<EntityResponseType> {
    return this.http.put<IAppConfiguration>(this.resourceUrl, appConfiguration, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAppConfiguration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppConfiguration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
