import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './bank-project.reducer';
import { IBankProject } from 'app/shared/model/bank-project.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankProjectUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankProjectEntity = useAppSelector(state => state.bankProject.entity);
  const loading = useAppSelector(state => state.bankProject.loading);
  const updating = useAppSelector(state => state.bankProject.updating);
  const updateSuccess = useAppSelector(state => state.bankProject.updateSuccess);
  const handleClose = () => {
    props.history.push('/bank-project');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bankProjectEntity,
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
      ? {}
      : {
          ...bankProjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankProject.home.createOrEditLabel" data-cy="BankProjectCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankProject.home.createOrEditLabel">Create or edit a BankProject</Translate>
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
                  id="bank-project-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bankProject.name')}
                id="bank-project-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankProject.projectType')}
                id="bank-project-projectType"
                name="projectType"
                data-cy="projectType"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankProject.initialValue')}
                id="bank-project-initialValue"
                name="initialValue"
                data-cy="initialValue"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankProject.currentValue')}
                id="bank-project-currentValue"
                name="currentValue"
                data-cy="currentValue"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-project" replace color="info">
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

export default BankProjectUpdate;
