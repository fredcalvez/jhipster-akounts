import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './bank-saving.reducer';
import { IBankSaving } from 'app/shared/model/bank-saving.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankSavingUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankSavingEntity = useAppSelector(state => state.bankSaving.entity);
  const loading = useAppSelector(state => state.bankSaving.loading);
  const updating = useAppSelector(state => state.bankSaving.updating);
  const updateSuccess = useAppSelector(state => state.bankSaving.updateSuccess);
  const handleClose = () => {
    props.history.push('/bank-saving');
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
    values.summaryDate = convertDateTimeToServer(values.summaryDate);

    const entity = {
      ...bankSavingEntity,
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
          summaryDate: displayDefaultDateTime(),
        }
      : {
          ...bankSavingEntity,
          summaryDate: convertDateTimeFromServer(bankSavingEntity.summaryDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankSaving.home.createOrEditLabel" data-cy="BankSavingCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankSaving.home.createOrEditLabel">Create or edit a BankSaving</Translate>
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
                  id="bank-saving-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bankSaving.summaryDate')}
                id="bank-saving-summaryDate"
                name="summaryDate"
                data-cy="summaryDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.bankSaving.amount')}
                id="bank-saving-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankSaving.goal')}
                id="bank-saving-goal"
                name="goal"
                data-cy="goal"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankSaving.reach')}
                id="bank-saving-reach"
                name="reach"
                data-cy="reach"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-saving" replace color="info">
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

export default BankSavingUpdate;
