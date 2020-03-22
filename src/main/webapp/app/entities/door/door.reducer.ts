import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDoor, defaultValue } from 'app/shared/model/door.model';

export const ACTION_TYPES = {
  FETCH_DOOR_LIST: 'door/FETCH_DOOR_LIST',
  FETCH_DOOR: 'door/FETCH_DOOR',
  CREATE_DOOR: 'door/CREATE_DOOR',
  UPDATE_DOOR: 'door/UPDATE_DOOR',
  DELETE_DOOR: 'door/DELETE_DOOR',
  RESET: 'door/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDoor>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DoorState = Readonly<typeof initialState>;

// Reducer

export default (state: DoorState = initialState, action): DoorState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DOOR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DOOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DOOR):
    case REQUEST(ACTION_TYPES.UPDATE_DOOR):
    case REQUEST(ACTION_TYPES.DELETE_DOOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DOOR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DOOR):
    case FAILURE(ACTION_TYPES.CREATE_DOOR):
    case FAILURE(ACTION_TYPES.UPDATE_DOOR):
    case FAILURE(ACTION_TYPES.DELETE_DOOR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DOOR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_DOOR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DOOR):
    case SUCCESS(ACTION_TYPES.UPDATE_DOOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DOOR):
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

const apiUrl = 'api/doors';

// Actions

export const getEntities: ICrudGetAllAction<IDoor> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DOOR_LIST,
    payload: axios.get<IDoor>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDoor> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DOOR,
    payload: axios.get<IDoor>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDoor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DOOR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDoor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DOOR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDoor> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DOOR,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
