import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITimeTracking, defaultValue } from 'app/shared/model/time-tracking.model';

export const ACTION_TYPES = {
  FETCH_TIMETRACKING_LIST: 'timeTracking/FETCH_TIMETRACKING_LIST',
  FETCH_TIMETRACKING: 'timeTracking/FETCH_TIMETRACKING',
  CREATE_TIMETRACKING: 'timeTracking/CREATE_TIMETRACKING',
  UPDATE_TIMETRACKING: 'timeTracking/UPDATE_TIMETRACKING',
  DELETE_TIMETRACKING: 'timeTracking/DELETE_TIMETRACKING',
  RESET: 'timeTracking/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITimeTracking>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TimeTrackingState = Readonly<typeof initialState>;

// Reducer

export default (state: TimeTrackingState = initialState, action): TimeTrackingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TIMETRACKING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TIMETRACKING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TIMETRACKING):
    case REQUEST(ACTION_TYPES.UPDATE_TIMETRACKING):
    case REQUEST(ACTION_TYPES.DELETE_TIMETRACKING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TIMETRACKING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TIMETRACKING):
    case FAILURE(ACTION_TYPES.CREATE_TIMETRACKING):
    case FAILURE(ACTION_TYPES.UPDATE_TIMETRACKING):
    case FAILURE(ACTION_TYPES.DELETE_TIMETRACKING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TIMETRACKING_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_TIMETRACKING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TIMETRACKING):
    case SUCCESS(ACTION_TYPES.UPDATE_TIMETRACKING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TIMETRACKING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/time-trackings';

// Actions

export const getEntities: ICrudGetAllAction<ITimeTracking> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TIMETRACKING_LIST,
    payload: axios.get<ITimeTracking>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITimeTracking> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TIMETRACKING,
    payload: axios.get<ITimeTracking>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITimeTracking> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TIMETRACKING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ITimeTracking> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TIMETRACKING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITimeTracking> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TIMETRACKING,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
