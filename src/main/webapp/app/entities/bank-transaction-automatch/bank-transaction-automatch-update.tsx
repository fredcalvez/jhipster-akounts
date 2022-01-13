import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankTransaction } from 'app/shared/model/bank-transaction.model';
import { getEntities as getBankTransactions } from 'app/entities/bank-transaction/bank-transaction.reducer';
import { IAutomatch } from 'app/shared/model/automatch.model';
import { getEntities as getAutomatches } from 'app/entities/automatch/automatch.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bank-transaction-automatch.reducer';
import { IBankTransactionAutomatch } from 'app/shared/model/bank-transaction-automatch.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankTransactionAutomatchUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankTransactions = useAppSelector(state => state.bankTransaction.entities);
  const automatches = useAppSelector(state => state.automatch.entities);
  const bankTransactionAutomatchEntity = useAppSelector(state => state.bankTransactionAutomatch.entity);
  const loading = useAppSelector(state => state.bankTransactionAutomatch.loading);
  const updating = useAppSelector(state => state.bankTransactionAutomatch.updating);
  const updateSuccess = useAppSelector(state => state.bankTransactionAutomatch.updateSuccess);
  const handleClose = () => {
    props.history.push('/bank-transaction-automatch');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankTransactions({}));
    dispatch(getAutomatches({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bankTransactionAutomatchEntity,
      ...values,
      transactionId: bankTransactions.find(it => it.id.toString() === values.transactionId.toString()),
      automatchId: automatches.find(it => it.id.toString() === values.automatchId.toString()),
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
          ...bankTransactionAutomatchEntity,
          transactionId: bankTransactionAutomatchEntity?.transactionId?.id,
          automatchId: bankTransactionAutomatchEntity?.automatchId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankTransactionAutomatch.home.createOrEditLabel" data-cy="BankTransactionAutomatchCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankTransactionAutomatch.home.createOrEditLabel">
              Create or edit a BankTransactionAutomatch
            </Translate>
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
                  id="bank-transaction-automatch-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="bank-transaction-automatch-transactionId"
                name="transactionId"
                data-cy="transactionId"
                label={translate('akountsApp.bankTransactionAutomatch.transactionId')}
                type="select"
              >
                <option value="" key="0" />
                {bankTransactions
                  ? bankTransactions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="bank-transaction-automatch-automatchId"
                name="automatchId"
                data-cy="automatchId"
                label={translate('akountsApp.bankTransactionAutomatch.automatchId')}
                type="select"
              >
                <option value="" key="0" />
                {automatches
                  ? automatches.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-transaction-automatch" replace color="info">
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

export default BankTransactionAutomatchUpdate;
