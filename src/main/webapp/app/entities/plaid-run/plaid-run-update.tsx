import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './plaid-run.reducer';
import { IPlaidRun } from 'app/shared/model/plaid-run.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaidRunUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const plaidRunEntity = useAppSelector(state => state.plaidRun.entity);
  const loading = useAppSelector(state => state.plaidRun.loading);
  const updating = useAppSelector(state => state.plaidRun.updating);
  const updateSuccess = useAppSelector(state => state.plaidRun.updateSuccess);
  const handleClose = () => {
    props.history.push('/plaid-run');
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
      ...plaidRunEntity,
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
          ...plaidRunEntity,
          runStart: convertDateTimeFromServer(plaidRunEntity.runStart),
          runEnd: convertDateTimeFromServer(plaidRunEntity.runEnd),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.plaidRun.home.createOrEditLabel" data-cy="PlaidRunCreateUpdateHeading">
            <Translate contentKey="akountsApp.plaidRun.home.createOrEditLabel">Create or edit a PlaidRun</Translate>
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
                  id="plaid-run-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.plaidRun.runType')}
                id="plaid-run-runType"
                name="runType"
                data-cy="runType"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidRun.runStatus')}
                id="plaid-run-runStatus"
                name="runStatus"
                data-cy="runStatus"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidRun.runItemId')}
                id="plaid-run-runItemId"
                name="runItemId"
                data-cy="runItemId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidRun.runStart')}
                id="plaid-run-runStart"
                name="runStart"
                data-cy="runStart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.plaidRun.runEnd')}
                id="plaid-run-runEnd"
                name="runEnd"
                data-cy="runEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plaid-run" replace color="info">
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

export default PlaidRunUpdate;
