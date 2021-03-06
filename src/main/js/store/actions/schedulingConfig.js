import {
    SCHEDULING_CONFIG_FETCHING,
    SCHEDULING_CONFIG_FETCHED,
    SCHEDULING_CONFIG_FETCH_ERROR,
    SCHEDULING_CONFIG_UPDATE_ERROR,
    SCHEDULING_CONFIG_UPDATING,
    SCHEDULING_CONFIG_UPDATED,
    SCHEDULING_ACCUMULATOR_ERROR,
    SCHEDULING_ACCUMULATOR_RUNNING,
    SCHEDULING_ACCUMULATOR_SUCCESS
} from './types';

import { verifyLoginByStatus } from './session';

const CONFIG_URL = '/alert/api/configuration/global/scheduling';
const ACCUMULATOR_URL = '/alert/api/configuration/global/scheduling/accumulator/run';

/**
 * Triggers Scheduling Config Fetching reducer
 * @returns {{type}}
 */
function fetchingSchedulingConfig() {
    return {
        type: SCHEDULING_CONFIG_FETCHING
    };
}

/**
 * Triggers Scheduling Config Fetched Reducer
 * @returns {{type}}
 */
function schedulingConfigFetched(config) {
    return {
        type: SCHEDULING_CONFIG_FETCHED,
        config
    };
}

function schedulingConfigFetchError(message) {
    return {
        type: SCHEDULING_CONFIG_FETCH_ERROR,
        error: {
            message
        }
    }
}

/**
 * Triggers Scheduling Config Updating reducer
 * @returns {{type}}
 */
function updatingSchedulingConfig() {
    return {
        type: SCHEDULING_CONFIG_UPDATING
    };
}

/**
 * Triggers Scheduling Config Updated Reducer
 * @returns {{type}}
 */
function schedulingConfigUpdated(config) {
    return {
        type: SCHEDULING_CONFIG_UPDATED,
        config
    };
}

/**
 * Triggers Scheduling Config Error
 * @returns {{type}}
 */
function schedulingConfigError(message, errors) {
    return {
        type: SCHEDULING_CONFIG_UPDATE_ERROR,
        message,
        errors
    };
}

/**
 * Triggers Scheduling Accumulator Running
 * @returns {{type}}
 */
function runningAccumulator() {
    return {
        type: SCHEDULING_ACCUMULATOR_RUNNING
    };
}

/**
 * Triggers Scheduling Accumulator Ran
 * @returns {{type}}
 */
function accumulatorSuccess() {
    return {
        type: SCHEDULING_ACCUMULATOR_SUCCESS
    };
}

/**
 * Triggers Scheduling Accumulator Ran
 * @returns {{type}}
 */
function accumulatorError(error) {
    return {
        type: SCHEDULING_ACCUMULATOR_ERROR,
        accumulatorError: error
    };
}

export function getSchedulingConfig() {
    return (dispatch, getState) => {
        dispatch(fetchingSchedulingConfig());
        const { csrfToken } = getState().session;
        fetch(CONFIG_URL, {
            credentials: 'same-origin',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        })
        .then((response) => {
            if(response.ok) {
                response.json().then((body) => {
                    if (body.length > 0) {
                        dispatch(schedulingConfigFetched(body[0]));
                    } else {
                        dispatch(schedulingConfigFetched({}));
                    }
                });
            } else {
                dispatch(verifyLoginByStatus(response.status));
            }
        })
        .catch(dispatch(schedulingConfigFetchError(console.error)));
    };
}

export function updateSchedulingConfig(config) {
    return (dispatch, getState) => {
        dispatch(updatingSchedulingConfig());

        const body = {
            ...config,
            id: 1
        };
        const { csrfToken } = getState().session;
        fetch(CONFIG_URL, {
            method: 'PUT',
            headers: {
                'content-type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            credentials: 'same-origin',
            body: JSON.stringify(body)
        }).then((response) => {
            if (response.ok) {
                response.json().then(() => dispatch(schedulingConfigUpdated({ ...config })));
            } else {
                response.json()
                    .then((data) => {
                        switch (response.status) {
                            case 400:
                                return dispatch(schedulingConfigError(data.message, data.errors));
                            case 412:
                                return dispatch(schedulingConfigError(data.message, data.errors));
                            default: {
                                dispatch(schedulingConfigError(data.message, null));
                                return dispatch(verifyLoginByStatus(response.status));
                            }
                        }
                    });
            }
        }).then(() => {
            dispatch(getSchedulingConfig());
        }).catch(console.error);
    };
}

export function runSchedulingAccumulator() {
    return (dispatch, getState) => {
        dispatch(runningAccumulator());
        const { csrfToken } = getState().session;
        fetch(ACCUMULATOR_URL, {
            credentials: 'same-origin',
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        }).then((response) => {
            if (!response.ok) {
                response.json().then((json) => {
                    dispatch(accumulatorError(json.message));
                });
                dispatch(verifyLoginByStatus(response.status));
            } else {
                dispatch(accumulatorSuccess());
            }
        }).then(() => {
            dispatch(getSchedulingConfig());
        }).catch((err) => {
            dispatch(accumulatorError(err));
        });
    };
}
