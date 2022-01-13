import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './bridge-run.reducer';
import { IBridgeRun } from 'app/shared/model/bridge-run.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Status } from 'app/shared/model/enumerations/status.model';

export const BridgeRunUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bridgeRunEntity = useAppSelector(state => state.bridgeRun.entity);
  const loading = useAppSelector(state => state.bridgeRun.loading);
  const updating = useAppSelector(state => state.bridgeRun.updating);
  const updateSuccess = useAppSelector(state => state.bridgeRun.updateSuccess);
  const statusValues = Object.keys(Status);
  const handleClose = () => {
    props.history.push('/bridge-run');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.runStart = convertDateTimeToServer(values.runStart);
    values.runEnd = convertDateTimeToServer(values.runEnd);

    const entity = {
      ...bridgeRunEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          runStart: displayDefaultDateTime(),
          runEnd: displayDefaultDateTime(),
        }
      : {
          runStatus: 'ToDo',
          ...bridgeRunEntity,
          runStart: convertDateTimeFromServer(bridgeRunEntity.runStart),
          runEnd: convertDateTimeFromServer(bridgeRunEntity.runEnd),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bridgeRun.home.createOrEditLabel" data-cy="BridgeRunCreateUpdateHeading">
            <Translate contentKey="akountsApp.bridgeRun.home.createOrEditLabel">Create or edit a BridgeRun</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="bridge-run-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bridgeRun.runType')}
                id="bridge-run-runType"
                name="runType"
                data-cy="runType"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeRun.runStatus')}
                id="bridge-run-runStatus"
                name="runStatus"
                data-cy="runStatus"
                type="select"
              >
                {statusValues.map(status => (
                  <option value={status} key={status}>
                    {translate('akountsApp.Status.' + status)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('akountsApp.bridgeRun.runStart')}
                id="bridge-run-runStart"
                name="runStart"
                data-cy="runStart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.bridgeRun.runEnd')}
                id="bridge-run-runEnd"
                name="runEnd"
                data-cy="runEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bridge-run" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BridgeRunUpdate;
