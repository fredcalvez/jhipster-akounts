import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './plaid-item.reducer';
import { IPlaidItem } from 'app/shared/model/plaid-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaidItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const plaidItemEntity = useAppSelector(state => state.plaidItem.entity);
  const loading = useAppSelector(state => state.plaidItem.loading);
  const updating = useAppSelector(state => state.plaidItem.updating);
  const updateSuccess = useAppSelector(state => state.plaidItem.updateSuccess);
  const handleClose = () => {
    props.history.push('/plaid-item');
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
    values.addedDate = convertDateTimeToServer(values.addedDate);
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...plaidItemEntity,
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
          addedDate: displayDefaultDateTime(),
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...plaidItemEntity,
          addedDate: convertDateTimeFromServer(plaidItemEntity.addedDate),
          updateDate: convertDateTimeFromServer(plaidItemEntity.updateDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.plaidItem.home.createOrEditLabel" data-cy="PlaidItemCreateUpdateHeading">
            <Translate contentKey="akountsApp.plaidItem.home.createOrEditLabel">Create or edit a PlaidItem</Translate>
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
                  id="plaid-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.plaidItem.itemId')}
                id="plaid-item-itemId"
                name="itemId"
                data-cy="itemId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidItem.institutionId')}
                id="plaid-item-institutionId"
                name="institutionId"
                data-cy="institutionId"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidItem.accessToken')}
                id="plaid-item-accessToken"
                name="accessToken"
                data-cy="accessToken"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.plaidItem.addedDate')}
                id="plaid-item-addedDate"
                name="addedDate"
                data-cy="addedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.plaidItem.updateDate')}
                id="plaid-item-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plaid-item" replace color="info">
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

export default PlaidItemUpdate;
