import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './trigger-run.reducer';
import { ITriggerRun } from 'app/shared/model/trigger-run.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TriggerRunUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const triggerRunEntity = useAppSelector(state => state.triggerRun.entity);
  const loading = useAppSelector(state => state.triggerRun.loading);
  const updating = useAppSelector(state => state.triggerRun.updating);
  const updateSuccess = useAppSelector(state => state.triggerRun.updateSuccess);
  const handleClose = () => {
    props.history.push('/trigger-run');
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
    values.addDate = convertDateTimeToServer(values.addDate);
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    const entity = {
      ...triggerRunEntity,
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
          addDate: displayDefaultDateTime(),
          startDate: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
        }
      : {
          ...triggerRunEntity,
          addDate: convertDateTimeFromServer(triggerRunEntity.addDate),
          startDate: convertDateTimeFromServer(triggerRunEntity.startDate),
          endDate: convertDateTimeFromServer(triggerRunEntity.endDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.triggerRun.home.createOrEditLabel" data-cy="TriggerRunCreateUpdateHeading">
            <Translate contentKey="akountsApp.triggerRun.home.createOrEditLabel">Create or edit a TriggerRun</Translate>
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
                  id="trigger-run-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.triggerRun.runType')}
                id="trigger-run-runType"
                name="runType"
                data-cy="runType"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.triggerRun.status')}
                id="trigger-run-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.triggerRun.addDate')}
                id="trigger-run-addDate"
                name="addDate"
                data-cy="addDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.triggerRun.startDate')}
                id="trigger-run-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.triggerRun.endDate')}
                id="trigger-run-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/trigger-run" replace color="info">
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

export default TriggerRunUpdate;
